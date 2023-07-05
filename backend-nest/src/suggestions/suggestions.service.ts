import { Logger, Injectable } from '@nestjs/common';
import { Suggestion } from './suggestions.interface';
import { readFileSync } from 'fs';
import path = require('path');
import { parse } from '@fast-csv/parse';
import { FullOptions, Searcher } from 'fast-fuzzy';
import { ConfigService } from '@nestjs/config';

// parsing locations from the address reference
interface PlacesRow {
  name: string;
  latitude: string;
  longitude: string;
}

@Injectable()
export class SuggestionsService {
  private readonly logger = new Logger(SuggestionsService.name);
  private targets: Array<PlacesRow> = [];
  private options = {
    limit: 100, // don't return more results than you need!
    threshold: -10000, // don't return bad results
  };
  private searcher: Searcher<Object, FullOptions<Object>> | undefined;

  constructor(private configService: ConfigService) {
    const nodeEnv = this.configService.get<string>('NODE_ENV');
    const filePath = (nodeEnv === 'test') ? './src/CAsmall.txt' : './src/CA.txt'
    const content = readFileSync(
      path.join(process.cwd(), filePath), { encoding: 'utf8'}
    );
    
    const stream = parse({ delimiter: '\t', quote: "'", escape: '    ' })
      .on('error', (error) => {
        this.logger.error(error);
      })
      .on('data', (row) => {
        const placedata: PlacesRow = {
          name: row[1],
          latitude: row[4],
          longitude: row[5],
        };
        this.targets.push(placedata);
      })
      .on('end', (rowCount: number) => {
        this.searcher = new Searcher(this.targets, {
          keySelector: (obj: PlacesRow) => {return obj.name}, threshold:0.8
        });
        this.logger.log('search initialized');
      });
    stream.write(content);
    stream.end();
    this.logger.log("data initialized")
  }

  async getSuggestion(
    query: string,
    latitude: string,
    longitude: string,
    page: number
  ): Promise<Array<Suggestion>> {
    let matches = Array<Suggestion>();
    this.logger.log("request for "+ query);
    if (this.searcher) {
      const results = this.searcher.search(query, { returnMatchData: true });
      results.forEach((e) => {
        const matchInfo = e.match;
        this.logger.log(e);
        // get the matches that start the sentance
        // if the lat lon is supplied, sort by shortest distance
        // if paged, slice by 5 and the page offset
        if (matchInfo.index === 0) {
            const place = <PlacesRow> e.item;
            matches.push({
              name: e.original,
              score: e.score,
              latitude: place.latitude,
              longitude: place.longitude,
            });
        }
      });
    }
    return matches;
  }
}
