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
    let res = service.getSuggestion('','','', 0);
    expect(res).resolves.toHaveLength(0);
  });

  it('suggestions', () => {
    let res = service.getSuggestion('vi','','', 0);
    expect(res).resolves.toHaveLength(3);
  });

});
