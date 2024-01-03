import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeFavoriteListComponent } from './recipe-favorite-list.component';

describe('RecipeFavoriteListComponent', () => {
  let component: RecipeFavoriteListComponent;
  let fixture: ComponentFixture<RecipeFavoriteListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecipeFavoriteListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RecipeFavoriteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
