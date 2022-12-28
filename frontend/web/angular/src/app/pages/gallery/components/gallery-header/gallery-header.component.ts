import { Component, EventEmitter, Output } from '@angular/core';
import { Image } from '../index/index.component';

@Component({
  selector: 'app-gallery-header',
  templateUrl: './gallery-header.component.html',
  styleUrls: ['./gallery-header.component.scss']
})
export class GalleryHeaderComponent {
  @Output() gallerySelected: EventEmitter<Image[]> = new EventEmitter<any>();
  public galleryItems: { name: string, description: string, images: Image[], selected: boolean }[] = [
    {
      name: 'All',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
          imageAlt: '1',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
          imageAlt: '2',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1584467735867-4297ae2ebcee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=705&q=80',
          imageAlt: '3',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1642649149963-0ef6779df6c6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '4',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1642618215095-3523a9a36893?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80',
          imageAlt: '5',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1514415679929-1fd5193f14f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '6',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1490730141103-6cac27aaab94?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '7',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1501854140801-50d01698950b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=975&q=80',
          imageAlt: '8',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80',
          imageAlt: '9',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1523712999610-f77fbcfc3843?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '10',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1470770903676-69b98201ea1c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '11',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1465189684280-6a8fa9b19a7a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '12',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1578496781985-452d4a934d50?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '13',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1631815587646-b85a1bb027e1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80',
          imageAlt: '7',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1629909613654-28e377c37b09?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1168&q=80',
          imageAlt: '8',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1605369473971-c5e417ac3220?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '9',
          imageTitle: ''
        }
      ],
      selected: true
    },
    {
      name: 'Urology',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
          imageAlt: '1',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
          imageAlt: '2',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1584467735867-4297ae2ebcee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=705&q=80',
          imageAlt: '3',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1642649149963-0ef6779df6c6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '4',
          imageTitle: ''
        }
      ],
      selected: false
    },
    {
      name: 'Neurology',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1642618215095-3523a9a36893?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80',
          imageAlt: '5',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1514415679929-1fd5193f14f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '6',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1490730141103-6cac27aaab94?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '7',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1501854140801-50d01698950b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=975&q=80',
          imageAlt: '8',
          imageTitle: ''
        }
      ],
      selected: false
    },
    {
      name: 'Orthopedic',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80',
          imageAlt: '9',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1523712999610-f77fbcfc3843?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '10',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1470770903676-69b98201ea1c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '11',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1465189684280-6a8fa9b19a7a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '12',
          imageTitle: ''
        }
      ],
      selected: false
    },
    {
      name: 'Cardiologist',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1578496781985-452d4a934d50?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '13',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1631815587646-b85a1bb027e1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80',
          imageAlt: '7',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1629909613654-28e377c37b09?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1168&q=80',
          imageAlt: '8',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1605369473971-c5e417ac3220?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '9',
          imageTitle: ''
        }
      ],
      selected: false
    },
    {
      name: 'Dentist',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      images: [
        {
          imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
          imageAlt: '1',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1514415679929-1fd5193f14f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '6',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1470770903676-69b98201ea1c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
          imageAlt: '11',
          imageTitle: ''
        },
        {
          imageSrc: 'https://images.unsplash.com/photo-1605369473971-c5e417ac3220?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
          imageAlt: '9',
          imageTitle: ''
        }
      ],
      selected: false
    }
  ];

  constructor() { }

  public onSelectGalleryItem(selectedGallery: { name: string, description: string, images: Image[], selected: boolean }): void {
    selectedGallery.selected = true;
    this.gallerySelected.emit(selectedGallery.images);
    this.galleryItems.forEach(gallery => {
      if (gallery !== selectedGallery) {
        gallery.selected = false;
      }
    });
  }

}
