import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awReclamation
    visible: true
    width: 640
    height: 480

    Text {
        id: textHeader
        text: "Réclamation"
        anchors.verticalCenterOffset: -50
        anchors.horizontalCenterOffset: 0
        font.bold: true
        font.pointSize: 18
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelReclamation
        x: 0
        text: "Laissez-nook savoir pourquoi vous êtes mécontents"
        anchors.verticalCenter: parent.verticalCenter
        anchors.horizontalCenter: parent.horizontalCenter
    }

    TextField {
        id: textFieldReclamation
        width: 350
        height: 22
        text: ""
        anchors.verticalCenterOffset: 30
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "Votre réclamation"
    }

    Button {
        id: buttonValider
        text: "Envoyer"
        anchors.verticalCenterOffset: 70
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }
}
