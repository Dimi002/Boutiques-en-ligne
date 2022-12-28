export interface IErrorObject {
  ErrorCode?: number;
  errorStatus?: string;
  errorText?: string;
  stringErrorCode?: string;
}

export class ErrorObject implements IErrorObject {
  constructor(
    public ErrorCode?: number,
    public errorStatus?: string,
    public errorText?: string,
    public stringErrorCode?: string
  ) { }
}
