import { HttpModule } from '@nestjs/axios';
import { Module } from '@nestjs/common';
import { PokemonService } from './pokemon.service';
import { PokemonController } from './pokemon.controller';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [HttpModule, ConfigModule],
  providers: [PokemonService],
  controllers: [PokemonController],
})
export class PokemonModule {}
