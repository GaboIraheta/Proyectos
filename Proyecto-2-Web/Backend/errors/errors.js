/**
 * Esto contiene todos los mensajes de error configurados en clases
 * para manejar los errores eficientemente
 */

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

export class GetChecklistFailed extends Error {
  constructor(message = "No se ha podido obtener los datos de requerimientos") {
    super(message);
  }
}

export class GetFormFailed extends Error {
  constructor(message = "No se han podido obtener los datos del formulario") {
    super(message);
  }
}

export class NotFoundConsultants extends Error {
  constructor(message = "No se han encontrado consultores para presentar") {
    super(message);
  }
}

export class ConsultantAlreadyExist extends Error {
  constructor(message = "El consultor ya existe") {
    super(message);
  }
}

export class NotFoundAdmin extends Error {
  constructor(message = "El administrador no ha sido encontrado") {
    super(message);
  }
}

export class NotFoundCheck extends Error {
  constructor(message = "El requerimiento no ha sido encontrado") {
    super(message);
  }
}

export class CheckAlreadyExists extends Error {
  constructor(message = "El requerimiento ya existe") {
    super(message);
  }
}

export class NotFoundForm extends Error {
  constructor(message = "La pregunta de formulario no ha sido encontrada") {
    super(message);
  }
}

export class FormAlreadyExists extends Error {
  constructor(message = "La pregunta de formulario ya existe") {
    super(message);
  }
}
