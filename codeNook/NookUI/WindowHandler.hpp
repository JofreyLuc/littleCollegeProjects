#ifndef WINDOWHANDLER_HPP
#define WINDOWHANDLER_HPP

#include <QObject>
#include <QQuickView>
#include <QQuickItem>

#include <Utilisateur.hpp>
#include <BDD.hpp>

#include <iostream>

class WindowHandler : public QObject {
    Q_OBJECT

private:
    Utilisateur *u;
    Bdd *bdd;

    QQuickView vRecherche;
    QQuickView vRechercheU;
    QQuickView vProfil;
    QQuickView vVente;
    QQuickView vConnexion;
    QQuickView vInscription;
    QQuickView vDeconnexion;

public:
    WindowHandler();
    void setUtilisateur(Utilisateur *u);
    void setBdd(Bdd *b);
    void setProfil();

public slots:
    void triggerRecherche();
    void triggerRechercheU();
    void triggerProfil();
    void triggerVente();
    void triggerConnexion();
    void triggerInscription();
    void triggerDeconnexion();
    void quit();
    void validerVente();
};

#endif
