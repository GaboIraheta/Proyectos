export class UserAlreadyExistError extends Error {
  constructor(message = "El usuario ya existe") {
    super(message);
  }
}

export class InvalidCredentialsError extends Error {
  constructor(message = "Credenciales inválidas") {
    super(message);
  }
}

export class NotFoundUsers extends Error {
  constructor(
    message = "El usuario no ha sido encontrado"
  ) {
    super(message);
  }
}