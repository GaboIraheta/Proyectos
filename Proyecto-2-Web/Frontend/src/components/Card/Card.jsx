import React from "react";
import "./Card.css";
import { NavLink } from "react-router-dom";

export default function Card({
  image,
  title,
  description,
  redirect,
  actionText = "Ver",
}) {
  return (
    <article className="card">
      <img src={image} alt={title} className="cardImage" />
      <div className="cardContent">
        <div className="cardHeader">
          <h3 className="cardTitle">{title}</h3>
          <img
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/66a441af6e812b59ebe8b73e540c8309f32ec3715a86e3e70f1b4639db7f808f"
            alt=""
            className="cardIcon"
          />
        </div>
        <p className="cardDescription">{description}</p>
      </div>
      <NavLink to={redirect} className="navLink">
        <button className="cardAction">{actionText}</button>
      </NavLink>
    </article>
  );
}
