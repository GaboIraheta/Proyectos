import React from "react";
import Card from "../../components/Card/Card";
import "../Profile/ProfileLayout.css";
import "./AdminDashboard.css";
import { redirect } from "react-router-dom";

const cards = [
  {
    image:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNVR6QMd3PBAU8yoWl8nlLKYoMOTF7zqH_bQ&s",
    title: "Administrar Usuarios",
    description: "Vista de usuarios",
    redirect: "/admin/users",
  },
  {
    image:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOsbNuCo9YMBkKk4khUq3XdI2dDZhpLrh2rA&s",
    title: "Administrar Consultores",
    description: "Operaciones con consultores",
    redirect: "/admin/consultants",
  },
  {
    image:
      "https://codideep.com/img/blogpost/imagenportada/202003020000001.png",
      title: "Administrar Preguntas",
      description: "Preguntas mostradas a los usuarios",
      redirect: "/admin/formPage"
  },
];

export const AdminMenuLayout = () => {
  return (
    <main className="profileContainer">
      {}
      <section className="profileHeader">
        <div className="headerBackground" />
        <div className="headerContent">
          <h1 className="user-title">Bienvenido al Panel de usuario administrador</h1>
        </div>
      </section>

      {}
      <div className="mainContent">
        <section className="cardsSection">
          <div className="cardsGrid">
            {cards.map((card, index) => (
              <Card key={index} {...card} />
            ))}
          </div>
        </section>
      </div>
    </main>
  );
};
