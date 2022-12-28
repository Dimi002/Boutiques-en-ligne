import { INavData } from "../models/menu.model";

export const navItems: INavData[] = [
  {
    name: 'Doctors',
    url: '/home/doctor-dashboard',
    children: [
      {
        name: 'Doctor Dashboard',
        url: '/home/doctor-dashboard',
        icon: 'fa-columns',
        roles: ['SPECIALIST']
      },
      {
        name: 'Appointments',
        url: '/home/doctor-appointments',
        icon: 'fa-calendar-check',
        roles: ['SPECIALIST']
      },
      {
        name: 'Schedule Timing',
        url: '/home/doctor-schedule-timing',
        icon: 'fa-hourglass-start',
        roles: ['SPECIALIST']
      },
      {
        name: 'Profile Settings',
        url: '/home/doctor-profile-settings',
        icon: 'fa-user-cog',
        roles: ['SPECIALIST']
      },
      {
        name: 'Social Media',
        url: '/home/doctor-social-media',
        icon: 'fa-share-alt',
        roles: ['SPECIALIST']
      },
      {
        name: 'Change Password',
        url: '/home/doctor-change-password',
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
