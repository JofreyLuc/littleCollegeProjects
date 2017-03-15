#include <QApplication>
#include <QQmlApplicationEngine>

#include <QQmlEngine>
#include <QQmlComponent>
#include <QObject>

#include "WindowHandler.hpp"

#include "Utilisateur.hpp"
#include "BDD.hpp"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    QQmlEngine engine;
    WindowHandler wh;

    QQmlComponent cMain(&engine, QUrl("qrc:/main.qml"));
    QObject *oMain = cMain.create();
    QObject::connect(&engine, SIGNAL(quit()), &wh, SLOT(quit()));

    QObject *sideBar = oMain->findChild<QObject*>("sideBar");

    QObject::connect(sideBar, SIGNAL(sig_recherche()), &wh, SLOT(triggerRecherche()));
    QObject::connect(sideBar, SIGNAL(sig_rechercheu()), &wh, SLOT(triggerRechercheU()));
    QObject::connect(sideBar, SIGNAL(sig_profil()), &wh, SLOT(triggerProfil()));
    QObject::connect(sideBar, SIGNAL(sig_vente()), &wh, SLOT(triggerVente()));
    QObject::connect(sideBar, SIGNAL(sig_connexion()), &wh, SLOT(triggerConnexion()));
    //QObject::connect(sideBar, SIGNAL(sig_inscription()), &wh, SLOT(triggerInscription()));
    //QObject::connect(sideBar, SIGNAL(sig_deconnexion()), &wh, SLOT(triggerDeconnexion()));

    /* Test */

    Bdd bdd;
    Utilisateur u;

    u.setBdd(&bdd);
    u.setNom("Abitbol");
    u.setPrenom("George");
    u.setPseudo("HommeLePlusClasseDuMonde");
    u.setMail("george.abitbol@laclasse.com");
    u.setMotDePasse("mondedemerde");
    u.setAdresseNum("foo");
    u.setAdresseCP("bar");
    u.setAdresseVille("Pom Pom Galli");
    u.setNumero("...");

    wh.setUtilisateur(&u);
    wh.setBdd(&bdd);
    wh.setProfil();

    return app.exec();
}
