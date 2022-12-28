import { INavData } from "../models/menu.model";

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/home/admin-dashboard',
    icon: 'fas fa-home',
    roles: ['ADMIN'],
  },
  {
    name: 'Appointments',
    url: '/home/admin-appointments',
    icon: 'fas fa-calendar-check',
    roles: ['ADMIN'],
  },
  {
    name: 'Specialities',
    url: '/home/admin-specialities',
    icon: 'fas fa-users',
    roles: ['ADMIN'],
  },
  {
    name: 'Doctors',
    url: '/home/admin-doctors',
    icon: 'fas fa-user-md',
    roles: ['ADMIN'],
  },
  {
    name: 'Patients',
    url: '/home/admin-patients',
    icon: 'fas fa-user-injured',
    roles: ['ADMIN'],
  },
  {
    name: 'Messages',
    url: '/home/admin-contacts',
    icon: 'fas fa-envelope',
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
    name: 'Doctor Dashboard',
    url: '/home/doctor-dashboard',
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