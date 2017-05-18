import { ArchiveAngPage } from './app.po';

describe('archive-ang App', () => {
  let page: ArchiveAngPage;

  beforeEach(() => {
    page = new ArchiveAngPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
