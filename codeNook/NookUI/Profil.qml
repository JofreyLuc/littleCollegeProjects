import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awProfil
    visible: true
    width: 740
    height: 480

    Text {
        id: textHeader
        text: "Mon Profil"
        font.bold: true
        anchors.horizontalCenterOffset: -90
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenterOffset: -170
        anchors.verticalCenter: parent.verticalCenter
        font.pointSize: 18
    }

    Text {
        id: textMessage1
        x: 10
        text: "Mes informations :"
        anchors.verticalCenterOffset: -120
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelPseudo
        x: 10
        text: "Nom d'utilisateur :"
        anchors.verticalCenterOffset: -90
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias pseudo: textContentPseudo.text
    Text {
        id: textContentPseudo
        x: 150
        text: "-"
        anchors.verticalCenterOffset: -90
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelMail
        x: 10
        text: "Adresse mail :"
        anchors.verticalCenterOffset: -70
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias mail: textContentMail.text
    Text {
        id: textContentMail
        x: 150
        text: "-"
        anchors.verticalCenterOffset: -70
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelAdresse
        x: 10
        text: "Adresse :"
        anchors.verticalCenterOffset: -50
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias adresse: textContentAdresse.text
    Text {
        id: textContentAdresse
        x: 150
        text: "-"
        anchors.verticalCenterOffset: -50
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelVille
        x: 10
        text: "Ville :"
        anchors.verticalCenterOffset: -30
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias ville: textContentVille.text
    Text {
        id: textContentVille
        x: 150
        text: "-"
        anchors.verticalCenterOffset: -30
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelTelephone
        x: 10
        text: "Téléphone :"
        anchors.verticalCenterOffset: -10
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias telephone: textContentTelephone.text
    Text {
        id: textContentTelephone
        x: 150
        text: "-"
        anchors.verticalCenterOffset: -10
        anchors.verticalCenter: parent.verticalCenter
    }

    Button {
        id: buttonModifierInfo
        x: 190
        text: "Modifier Informations"
        anchors.right: parent.right
        anchors.rightMargin: 50
        anchors.verticalCenterOffset: -50
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelNote
        x: 10
        text: "Note moyenne :"
        anchors.verticalCenterOffset: 20
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textContentNote
        x: 150
        text: "-"
        anchors.verticalCenterOffset: 20
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelAchats
        x: 10
        y: 300
        text: "Mes achats récents :"
    }

    Text {
        id: textLabelVentes
        x: 10
        text: "Mes ventes en cours :"
        anchors.verticalCenterOffset: 110
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelVentesRecentes
        x: 10
        text: "Mes ventes récentes :"
        anchors.verticalCenterOffset: 150
        anchors.verticalCenter: parent.verticalCenter
    }
}
