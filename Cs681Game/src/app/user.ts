export class User {
  userName: string;
  emailAddress: string;
  userPassword: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;

  constructor(username: string, email: string, password: string, firstName: string, lastName: string, phoneNumber: string) {
    this.userName = username;
    this.emailAddress = email;
    this.userPassword = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
  }
}
