export class LoginResponse {
    token?: string;
    username?: string;
    message?: string;
    error?: string;

    constructor(token?: string, username?: string, message?: string, error?: string) {
        this.token = token;
        this.username = username;
        this.message = message;
        this.error = error;
    }

}