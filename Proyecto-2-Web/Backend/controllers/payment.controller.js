
import { config } from "../config/config.js";
import axios from "axios";

/**
 * La función sirve como controller para el post en /create-order
 * se encargara de darle formato a la orden y mandarla a paypal, donde realizará la autenticación
 * Finalmente retornará un objeto donde se encontrará el link donde se realizará el pago
 * */
export const createOrder = async (req, res) => {
    const { price } = req.body; //Recibe el precio del consultor a través de la data al realizar el post
    const order = { //Genera la estructura del pedido a realizar
        intent: "CAPTURE",
        purchase_units: [
            {
                amount: {
                    currency_code: "USD",
                    value: price
                }
            }
        ],
        application_context: {
            brand_name: "FormCorp",
            landing_page: "NO_PREFERENCE",
            user_action: "PAY_NOW",
            return_url: `${config.HOST}/api/payment/capture-order`,
            cancel_url: `${req.headers.referer}`,

        }
    }
    const params = new URLSearchParams();
    params.append('grant_type', 'client_credentials');
    const { data: { access_token } } = await axios.post(`${config.paypalApi}/v1/oauth2/token`, params, {
        auth: {
            username: config.paypalClientKey,
            password: config.paypalSecretKey,
        }
    })
    //Realiza la autenticación del pedido con las credenciales del sanbox de paypal (las credenciales
    // son por parte de la api de paypal)


    //Una vez realizada la autenticación, se procede a hacer el pedido con el token 
    const response = await axios.post(`${config.paypalApi}/v2/checkout/orders`, order, {
        headers: {
            Authorization: `Bearer ${access_token}`
        }
    });


    //Finalmente devuelve la respuesta por parte de la creación de la orden
    //El frontEnd idealmente debe de realizar la redirección de la siguiente forma:
    //window.location.href = data.links[1].href
    return res.json(response.data);
}

export const captureOrder = async (req, res) => {
    const { token } = req.query;

    try {
        //Genera la URL a la que la api mandará la respuesta
        const redirectURL = process.env.HOST + process.env.CLIENT_PORT;


        //Redirecciona a la página original, esta vez pasando como parámetros el token, la url de la api de paypal,
        //el ClientKey de paypal, y la SecretKey de paypal (Revisar si es seguro)
        return res.redirect(
            //Se deja así de largo porque si se pone entre lineas pues se desconfigura y devuelve mal el link
            `${redirectURL}/consultants?token=${token}&confirmation=${config.paypalApi}&user=${config.paypalClientKey}&psw=${config.paypalSecretKey}`
        );
    } catch (error) {
        console.error(error);
        return res.status(500).json({
            success: false,
            message: 'Error al procesar el pago',
        });
    }
}
