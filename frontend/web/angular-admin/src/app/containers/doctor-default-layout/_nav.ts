import { INavData } from "../models/menu.model";

export const navItems: INavData[] = [
  {
    name: 'Produits',
    url: '/home/boutique-dashboard',
    children: [
      {
        name: 'Produits Dashboard',
        url: '/home/boutique-dashboard',
        icon: 'fa-columns',
        roles: ['SPECIALIST']
      },
      {
        name: 'Profile Settings',
        url: '/home/boutique-profile-settings',
        icon: 'fa-user-cog',
        roles: ['SPECIALIST']
      },
      {
        name: 'Social Media',
        url: '/home/boutique-social-media',
        icon: 'fa-share-alt',
        roles: ['SPECIALIST']
      },
      {
        name: 'Change Password',
        url: '/home/boutique-change-password',
        icon: 'fa-lock',
        roles: ['SPECIALIST']
      },
      {
        name: 'admin Dashboard',
        url: '/home/admin-dashboard',
        roles: ['ADMIN'],
        icon: 'fa-home'
      }
    ]
  }
];
