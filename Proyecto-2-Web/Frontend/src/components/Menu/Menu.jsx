import React from 'react'
import './Menu.css'
import { NavLink } from 'react-router-dom'

const Menu = () => {
  return (
    <div className="NavBars">
        <ul className='NavbarWrappers'>
            <li className='NavBarElement'>
                <NavLink className='Link' to='/'>Servicios</NavLink>
            </li>
            <li className='NavBarElement'>
                <NavLink className='Link' to='/'>Sobre nosotros</NavLink>
            </li>
            <li className='NavBarElement'>
                <NavLink className='Link' to='/'>Preguntas</NavLink>
            </li>
            <li className='NavBarElement'>
                <NavLink className='Link' to='/'>Iniciar sesion</NavLink>
            </li>
            <li className='NavButton'>
                <NavLink className='linkbtn' to='/'>Registrarse</NavLink>
            </li>
        </ul>
    </div>
  )
}

export default Menu
