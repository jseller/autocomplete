import { Module } from '@nestjs/common';
import { SuggestionsController } from './suggestions.controller';
import { SuggestionsService } from './suggestions.service';
import { ConfigModule } from '@nestjs/config';


@Module({
  imports: [ConfigModule],
  controllers: [SuggestionsController],
  providers: [SuggestionsService]
})
export class SuggestionsModule {}
