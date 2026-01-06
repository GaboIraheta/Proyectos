import jwt from 'jsonwebtoken';
import nodemailer from 'nodemailer';
import { config } from '../config/config.js';

export const createToken = (user, recover=false) => {

    let _expiresIn = '1h'

    if (recover) {
        _expiresIn = '15m'
    }

    return jwt.sign(
        { id : user._id },
        config.jwtSecret,
        { expiresIn: _expiresIn }
    )
} 

import sgMail from '@sendgrid/mail';

export const sendEmail = async (addressee, affair, html) => {

    sgMail.setApiKey(config.API_KEY);

    await sgMail.send({
        to : addressee,
        from : config.EMAIL_USER,
        subject : affair,
        html : html
    });
}