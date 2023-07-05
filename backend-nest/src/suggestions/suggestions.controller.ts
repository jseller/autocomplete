import { Controller, Get, Param, Query } from '@nestjs/common';
import { SuggestionsService } from './suggestions.service';
import { SuggestionIdPipe } from './suggestion-id.pipe';


@Controller('suggestions')
export class SuggestionsController {
    constructor(private readonly suggestionsService: SuggestionsService) {}

    @Get()
    getSuggestion(@Query("q") query: string, @Query("latitude") lat: string, @Query("longitude") lon: string, @Query("page") page: number) {
      return this.suggestionsService.getSuggestion(query, lat, lon, page);
    }
  

}
