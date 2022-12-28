import { INavData } from "../models/menu.model";

export const navItems: INavData[] = [
  {
    name: 'Accueil',
    url: '/home'
  },
  {
    name: 'Consultations',
    url: '/take-appointment'
  },
  {
    name: 'Departements',
    url: '/departments/all'
  },
  {
    name: 'Galerie',
    url: '/gallery'
  },
  {
    name: 'AProposDeNous',
    url: '/about-us'
  },
  {
    name: 'ContactezNous',
    url: '/contact-us'
  }
];