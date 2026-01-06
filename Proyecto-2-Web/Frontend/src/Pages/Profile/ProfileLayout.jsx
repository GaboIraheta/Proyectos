import React from "react";
import "./ProfileLayout.css";
import Card from "../../components/Card/Card";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";

const cards = [
  {
    image:
      "https://assets.isu.pub/document-structure/230327022004-585805b7b824e93020d1cf355243b532/v1/9ef2a15a9c6405ce3a10bc89204b6406.jpeg",
    title: "Requerimientos",
    description: "¡Todo lo necesario para tu permiso!",
    redirect: "/requirements",
  },
  {
    image:
      "https://agenciaingenium.cl/wp-content/uploads/2024/08/conclusiones-1.webp",
    title: "Ficha",
    description: "¡Debes completarla paso a paso!",
    redirect: "/form",
  },
  {
    image:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOsbNuCo9YMBkKk4khUq3XdI2dDZhpLrh2rA&s",
    title: "Contrataciones",
    description: "¡Por si necesitas más ayuda!",
    redirect: "/consultants",
  },
];

export const ProfileLayout = () => {  
  return (
    <section className="profileContainer">
      { }
      <section className="profileHeader">
        <div className="headerBackground" />
        <div className="headerContent">
        <h2>Bienvenido a tu panel de gestión de permisos</h2>
        </div>
      </section>

        <div className="mainContent">
          <section className="cardsSection">
            <div className="cardsGrid">
              {cards.map((card, index) => (
                <Card key={index} {...card} />
              ))}
            </div>
          </section>
      </div>
    </section>
  );
};
