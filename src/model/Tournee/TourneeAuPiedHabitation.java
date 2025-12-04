package model.Tournee;

import controller.CamionController;
import model.map.Itineraire;
import model.map.Plan;
import model.map.Station;
import view.PlanView;

import java.util.ArrayList;
import java.util.List;

public class TourneeAuPiedHabitation {

    Plan plan;
    PlanView planV;
    CamionController camionController;
    Dijkstra dijkstra;

    private final double DECHETS_PAR_ARRET = 150.0;

    public TourneeAuPiedHabitation(Plan plan, CamionController cc) {
        this.plan = plan;
        this.camionController = cc;
        this.planV = new PlanView();
        this.dijkstra = new Dijkstra(plan);
    }

}
