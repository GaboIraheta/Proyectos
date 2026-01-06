import Checklist from "../models/checklist.model.js";
import Consultant from "../models/consultant.model.js";
import Form from "../models/form.model.js";
import Admin from "../models/admin.model.js";

/**
 * initializeData es un metodo que existe unicamente para inicializar 
 * la base de datos con datos de prueba para los objetos que requieren
 * inicializacion de datos directamente, al desplegar la aplicacion
 * no sera necesario dado que el administrador sera el responsable
 * de mantener actualizada esta informacion
 * El archivo solo sera disponible en casos de prueba
 */
export async function initializeData() {
	const counterChecklist = await Checklist.countDocuments();
	const counterConsultants = await Consultant.countDocuments();
	const counterForm = await Form.countDocuments();
	const counterAdmin = await Admin.countDocuments();

	if (counterAdmin === 0) {
		const defaultData = {
			username: 'Administrador',
			email: 'admin@gmail.com',
			password: '832A1wEwTG$',
		};

		try {
			Admin.create(defaultData);
		} catch (error) {
			throw new error;
		}
	}

	if (counterChecklist === 0) {

		const defaultDataChecks = {
			checks: [
				{ name: "Paso 1", description: "Formato de Solicitud.", order: 1 },
				{ name: "Paso 3", description: "Declaración Jurada autorizada por el Ministerio de Salud.", order: 3 },
				{ name: "Paso 2", description: "Ficha sanitaria de acuerdo con el tipo de establecimiento, mediante la cual  presenta autoevaluación de las condiciones sanitarias del establecimiento para el cual solicita el permiso.", order: 2 },
				{ name: "Paso 4", description: "Comprobante de pago del arancel.", order: 4 },
				{ name: "Paso 5", description: "Copia del Documento Único de Identidad del representante legal o persona natural, en caso de extranjeros Carnet de Residente o Pasaporte.", order: 5 },
				{ name: "Paso 6", description: "Copia de Escritura de Constitución de Sociedad o modificación de ésta.", order: 6 },
				{ name: "Paso 7", description: "Certificados de salud de los manipuladores de alimentos.", order: 7 },
				{ name: "Paso 8", description: "Croquis y distribución de la planta (para fabricas alimentarias)", order: 8 },
				{ name: "Paso 9", description: "Programa de capacitación de BPM (para fabricas alimentarias)", order: 9 },
				{ name: "Paso 10", description: "Incluir memoria descriptiva del proyecto. (no necesaria para vehículos)", order: 10 },
			]
		};

		try {
			Checklist.create(defaultDataChecks);
		} catch (error) {
			throw new error;
		}
	}

	if (counterConsultants === 0) {

		const defaultDataConsultants = {
			consultants: [

				{ username: "DanielSosa", email: "dasosa@gmail.com", phone: "78533397999", price: 100 },
				{ username: "Enrique Arguello", email: "earguello@gmail.com", phone: "0987654321", price: 200 },
				{ username: "Marta Merlos", email: "martam@gmail.com", phone: "1122334455", price: 300 },
				{ username: "Jacobo Abulladare", email: "jacoa@gmail.com", phone: "2233445566", price: 400 },
				{ username: "Gloria Galo", email: "gloriag@gmail.com", phone: "3344556677", price: 500 },
			]
		};

		try {
			Consultant.create(defaultDataConsultants);
		} catch (error) {
			throw new error;
		}
	}

	if (counterForm === 0) {

		const defaultDataForm = {
			forms: [
				{ question: "Situado en zonas o lugares no expuestos a contaminación.", 
					image: "https://i.pinimg.com/736x/a5/8b/ac/a58bacafe08366c8a592a91a2db04506.jpg",
					order: 1 },
				{ question: "Alrededores y áreas exteriores están limpios, libres de maleza, estancamiento de agua, promontorios de basura y polvo.",
					image: "https://www.abasturhub.com/img/blog/restaurantes-al-aire-libre-outdooors.jpg",
					 order: 2 },
				{ question: "El equipo se encuentra en condiciones óptimas de funcionamiento.", 
					image: "https://blog.imberacooling.com/hubfs/8_NOVIEMBRE_VOY_A_PONER_UN_RESTAURANTE_COMO_DEBO_REFRIGERAR_MIS_ALIMENTOS_v1.jpg",
					order: 3 },
				{ question: "Pisos de materiales que no contaminan los alimentos, facilitan procesos de limpieza y sanitización, sin daños ni grietas.", 
					image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSia6F2By7LvDRpSPR76g39bFNvn826nhaVLg&s", 
					order: 4 },
				{ question: "El área de preparación cumple con las normas de higiene.", 
					image: "https://theoctopusguide.discefa.com/wp-content/uploads/2021/06/caracteristicas-ideales-que-deben-tener-una-cocina.jpg",
					order: 5 },
				{ question: "Techos están en buen estado. Adecuado mantenimiento. No acumulan suciedad. Sin filtración de agua, goteras ni desprendimiento de partículas.", 
					image: "https://www.materialescalabuig.com/wp-content/uploads/2021/04/islas-restaurante.jpg",
					order: 5 },
			]
		};

		try {
			Form.create(defaultDataForm);
		} catch (error) {
			throw new error;
		}
	}
}