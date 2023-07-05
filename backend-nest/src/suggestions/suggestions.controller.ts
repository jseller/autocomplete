import { Controller, Get, Param, Query } from '@nestjs/common';
import { SuggestionsService } from './suggestions.service';
import { SuggestionIdPipe } from './suggestion-id.pipe';


@Controller('suggestions')
export class SuggestionsController {
    constructor(private readonly suggestionsService: SuggestionsService) {}

    @Get()
    getSuggestion(@Query("q") query: string, @Query("lat") lat: string, @Query("lon") lon: string) {
      return this.suggestionsService.getSuggestion(query, lat, lon);
    }
  

}
