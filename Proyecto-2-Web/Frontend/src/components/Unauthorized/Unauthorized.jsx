import "./Unauthorized.css";
import { Link, NavLink } from "react-router-dom";

const Unauthorized = () => {
  return (
    <>
      <section className="Unauthorized">
        <h1>¿Sin autorización?</h1>
        <h2>Será mejor que regresemos antes que alguien se de cuenta 👀</h2>

        <NavLink className="Link" to="/">
          <button>Regresar</button>
        </NavLink>
      </section>
    </>
  );
};

export default Unauthorized;
