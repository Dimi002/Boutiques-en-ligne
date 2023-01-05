import { INavData } from "../models/menu.model";

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/home/admin-dashboard',
    icon: 'fas fa-home',
    roles: ['ADMIN'],
  },
  {
    name: 'Boutiques',
    url: '/home/admin-boutique',
    icon: 'fas fa-users',
    roles: ['ADMIN'],
  },
  {
    name: 'Produits',
    url: '/home/admin-produits',
    icon: 'fas fa-user-md',
    roles: ['ADMIN'],
  },
  {
    name: 'Users',
    url: '/home/admin-users',
    icon: 'fas fa-user',
    roles: ['ADMIN'],
  },
  {
    name: 'Roles',
    url: '/home/admin-roles',
    icon: 'fas fa-users-cog',
    roles: ['ADMIN'],
  },
  {
    name: 'Permissions',
    url: '/home/admin-permissions',
    icon: 'fas fa-shield-alt',
    roles: ['ADMIN'],
  },
  {
    name: 'Boutique Dashboard',
    url: '/home/boutique-dashboard',
    roles: ['SPECIALIST'],
    icon: 'fas fa-home'
  },
  {
    name: 'Website Settings',
    url: '/home/admin-settings',
    roles: ['ADMIN'],
    icon: 'fas fa-cogs'
  }
];