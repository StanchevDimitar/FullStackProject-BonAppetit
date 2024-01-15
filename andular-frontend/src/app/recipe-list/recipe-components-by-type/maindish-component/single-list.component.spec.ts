import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SingleListComponent} from './single-list.component';

describe('MaindishComponentComponent', () => {
    let component: SingleListComponent;
    let fixture: ComponentFixture<SingleListComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [SingleListComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(SingleListComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
