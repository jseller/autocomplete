import { Test, TestingModule } from '@nestjs/testing';
import { SuggestionsService } from './suggestions.service';
import { ConfigModule } from '@nestjs/config';


describe('SuggestionsService', () => {
  let service: SuggestionsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [SuggestionsService],
      imports: [ConfigModule],
    }).compile();

    service = module.get<SuggestionsService>(SuggestionsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('suggestions', () => {
    let res = service.getSuggestion('','','');
    expect(res).resolves.toHaveLength(0);
  });

});
