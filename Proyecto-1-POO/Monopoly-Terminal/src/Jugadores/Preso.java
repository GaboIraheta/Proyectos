package Jugadores;

public class Preso {
    /* clase preso que permite llevar un control eficiente de los jugadores que se encuentran presos
     */
    private Jugador preso; //almacena el jugador que se va preso
    private int turnosPreso; //almacena los turnos que esta preso para darle salida en el turno indicado

    /**@param preso
     * metodo constructor de la clase
     * */
    public Preso(Jugador preso) {
        this.preso = preso;
        turnosPreso = 0;
    }

    //setters y getters
    public Jugador getPreso() {
        return preso;
    }
    public int getTurnosPreso() {
        return turnosPreso;
    }
    public void setTurnosPreso(int turnosPreso) {
        this.turnosPreso = turnosPreso;
    }


}
