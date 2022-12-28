export interface ILoginData {
  username?: string;
  password?: string;
  rememberMe?: boolean;
}

export class LoginData implements ILoginData {
  constructor(
    public login?: string,
    public password?: string,
    public rememberMe?: boolean
  ) { }
}
