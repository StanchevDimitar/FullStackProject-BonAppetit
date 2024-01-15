import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../auth/services/auth.service";
import {CommonModule} from "@angular/common";


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    imports: [
        RouterLink,
        CommonModule
    ],
    standalone: true
})
export class HeaderComponent {
    @Input() pageTitle!: string;

    constructor(protected authService: AuthService) {
    }

    logout() {
        this.authService.logout();
    }
}
