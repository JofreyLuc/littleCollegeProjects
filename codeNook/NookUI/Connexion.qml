import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awConnexion
    visible: true
    width: 640
    height: 480

    Text {
        id: textHeader
        x: 30
        text: "Se connecter"
        font.bold: true
        anchors.verticalCenterOffset: -40
        anchors.verticalCenter: parent.verticalCenter
        font.pointSize: 18
    }

    Text {
        id: textLabelPseudo
        x: 30
        text: "Nom d'utilisateur/mail :"
        anchors.verticalCenter: parent.verticalCenter
    }

    TextField {
        id: textFieldPseudo
        x: 200
        width: 150
        text: ""
        placeholderText: "Utilisateur"
        anchors.verticalCenterOffset: 0
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelMotDePasse
        x: 30
        text: "Mot de passe :"
        anchors.verticalCenterOffset: 30
        anchors.verticalCenter: parent.verticalCenter
    }

    TextField {
        id: textFieldMotDePasse
        x: 200
        width: 150
        text: ""
        placeholderText: "Mot de passe"
        anchors.verticalCenterOffset: 30
        anchors.verticalCenter: parent.verticalCenter
    }
}
