import { Module } from '@nestjs/common';
import { TweetsModule } from './tweets/tweets.module';
import { PokemonModule } from './pokemon/pokemon.module';
import { AuthModule } from './auth/auth.module';
import { ConfigModule } from "@nestjs/config";
import { SuggestionsModule } from './suggestions/suggestions.module';

@Module({
  imports: [TweetsModule, PokemonModule, AuthModule, ConfigModule.forRoot(), SuggestionsModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
