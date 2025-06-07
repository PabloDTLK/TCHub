export class RegisterResponse {
    username?: string;
    error?: string;

    constructor(username?: string, error?: string) {
        this.username = username;
        this.error = error;
    }

}