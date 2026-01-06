create database dbBCN;
use dbBCN;

create table Cliente (
	id int primary key auto_increment,
    nombres varchar(70),
    apellidos varchar(70),
    direccion text,
    numTelefono varchar(20)
);

create table Tarjeta (
	id int primary key auto_increment,
    clienteID int not null,
    foreign key (clienteID) references Cliente (id) on delete cascade,
    numTarjeta varchar(19),
    fechaExp date,
    tipo text,
    facilitadorID int
);

create table Facilitador (
	id int primary key auto_increment,
    facilitador text
);

create table Compra (
	id int primary key auto_increment,
    fechaCompra date,
    monto decimal(10,2),
    descripcion text,
    tarjetaID int not null,
    foreign key (tarjetaID) references Tarjeta (id) on delete cascade
);

alter table Tarjeta add constraint FK_facilitador foreign key (facilitadorID) 
references Facilitador (id) on delete cascade;

insert into Cliente (nombres, apellidos, numTelefono) values ('Gabriel Ernesto', 'Iraheta Guardado', '1618-0339'),
('Diego Benjamin', 'Garcia Alfaro', '6022-1023'),
('Mauricio Alejandro', 'Montes Varela', '3141-5926'),
('Miriam Rocio', 'Doñas Lopez', '2718-2818');

insert into Facilitador (facilitador) values ('Visa'),
('Mastercard'),
('AmericanExpress');


insert into Tarjeta (ClienteID, numTarjeta, fechaExp, tipo, facilitadorID) values 
(1, '1234 5678 9101 1121', '2026-11-12', 'Credito', 2),
(1, '1234 5678 9101 2707', '2026-06-19', 'Debito', 3),
(2, '1234 5678 9101 3141', '2027-03-27', 'Credito', 1),
(2, '1234 5678 9101 7890', '2027-04-13', 'Debito', 3),
(3, '1234 5678 9101 1681', '2028-07-09', 'Credito', 1),
(3, '1234 5678 9101 8945', '2026-12-12', 'Debito', 2),
(4, '1234 5678 9101 8523', '2025-11-30', 'Credito', 1),
(4, '1234 5678 9101 7413', '2028-10-27', 'Debito', 2); 

insert into Compra values (1, '2025-01-12', 18.35, 'Starbucks', 8),
(2, '2025-09-04', 20.50, 'Compra zapatos', 5),
(3, '2025-01-13', 11.75, 'Compra ropa', 1),
(4, '2024-11-03', 21.45, 'Compra ropa', 7),
(5, '2025-12-01', 15.00, 'Pago restaurante', 6),
(6, '2025-10-03', 154.00, 'Pago universidad', 1),
(7, '2024-10-30', 75.30, 'Compra de libro', 5),
(8, '2024-09-15', '5.75', 'Compra de materiales hijos', 4),
(9, '2024-08-16', 10.25, 'Compra de almuerzo', 4),
(10, '2025-01-01', 25.00, 'Regalo de abuela', 7),
(11, '2024-10-05', 12.12, 'Pago de libro', 5);

-- Consulta para reporte A (Parámetros: id de cliente, rango de fechas)
select c.* from Compra c inner join Tarjeta t on t.id = c.tarjetaID
inner join Cliente on t.ClienteID = Cliente.id 
where c.fechaCompra between '2024-01-01' and '2024-12-31' and t.ClienteID = 4; 

-- Consulta para reporte B (Parámetros: ID de cliente, fecha para sacar mes y año)
select sum(c.monto) from Compra c inner join Tarjeta t on t.id = c.tarjetaID
inner join Cliente on t.clienteID = Cliente.id 
where year(c.fechaCompra) = year('2024-10-15') and month(c.fechaCompra) = month('2024-10-15');

-- Consulta reporte C parte 1 (parámetros: tipo y CLienteID)
select numTarjeta from Tarjeta 
where tipo like 'Credito' and ClienteID = 1;
-- Consulta reporte C parte 2 (mismos parámetros)
select numTarjeta from Tarjeta
where tipo like 'Debito' and ClienteID = 1;

-- Consulta para reporte D (Parámetros: facilitador)
with TarjetaIDS (id) 
(select t.id from Tarjeta t inner join Facilitador f on t.facilitadorID = f.id where f.facilitador = ?),
ComprasCliente (id, numCompras, totalGastado) ass
(select t.id, count(c.id), sum(c.monto) from Compra c inner join TarjetaIDS t on t.id = c.tarjetaID group by t.id),
Clientes (id, compras, total) as
(select c.clienteID, t.numCompras, t.totalGastado from ComprasCliente t inner join Tarjeta c on t.id = c.id)
select c.id, c.nombres, c.apellidos, c.numTelefono, t.compras, t.total
from Cliente c inner join Clientes t on t.id = c.id