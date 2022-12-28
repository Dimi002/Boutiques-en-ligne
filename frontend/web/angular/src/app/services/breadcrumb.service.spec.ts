import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA} from '@angular/core';
import { BreadcrumbService } from './breadcrumb.service'
import { TranslateModule } from '@ngx-translate/core';
import { Breadcrumb } from '../additional-models/breadcrumb.model';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { filter } from 'rxjs';


describe('Service:  Breadcrumb Service', () => {

  let service: BreadcrumbService;
  let breadcrumbs: Breadcrumb[];
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule],
      providers: [BreadcrumbService],
      schemas: [ NO_ERRORS_SCHEMA ],
    });

    // initiallisation des variables
    breadcrumbs = []
    service = TestBed.inject(BreadcrumbService);
  });

  it('should create the Breadcrumb service', () => {
    expect(service).toBeTruthy();
  });


  it('should initialize  the Breadcrumb service', () => {
    service.breadcrumbs$.subscribe(res => {
      breadcrumbs = res;
    });
  });

  it('shoulld initialize breancumbRouter', () => {
  //   router.events.pipe(
  //     // Filter the NavigationEnd events as the breadcrumb is updated only when the route reaches its end
  //     filter((event) => event instanceof NavigationEnd)
  // ).subscribe(event => {
  //     // Construct the breadcrumb hierarchy
  //     const root = router.routerState.snapshot.root;
  //     const breadcrumbs: Breadcrumb[] = [];
  //     service.addBreadcrumb(root, [], breadcrumbs);

  //     // Emit the new hierarchy
  //     this._breadcrumbs$.next(breadcrumbs);
  // });
  });


});
