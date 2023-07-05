import { Module } from '@nestjs/common';
import { ConfigModule } from "@nestjs/config";
import { SuggestionsModule } from './suggestions/suggestions.module';

@Module({
  imports: [ConfigModule.forRoot(), SuggestionsModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
