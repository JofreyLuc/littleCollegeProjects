import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awNoter
    visible: true
    width: 640
    height: 480

    Text {
        id: textHeader
        text: "Noter"
        anchors.verticalCenterOffset: -120
        anchors.horizontalCenterOffset: 0
        font.bold: true
        font.pointSize: 18
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelNote
        x: 10
        text: "Quelle notes souhaitez vous donner ?"
        anchors.verticalCenterOffset: -70
        anchors.verticalCenter: parent.verticalCenter
    }

    Slider {
        id: sliderNote
        x: 10
        tickmarksEnabled: true
        stepSize: 1
        minimumValue: 5
        value: 5
        anchors.verticalCenterOffset: -40
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelCommentaire
        x: 10
        text: "Commentaire :"
        anchors.verticalCenterOffset: 10
        anchors.verticalCenter: parent.verticalCenter
    }

    TextField {
        id: textFieldCommentaire
        x: 10
        width: 270
        height: 22
        text: ""
        clip: false
        placeholderText: "Saisir votre commentaire"
        anchors.verticalCenterOffset: 40
        anchors.verticalCenter: parent.verticalCenter
    }

    Button {
        id: buttonValider
        x: 0
        text: "Envoyer"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenterOffset: 90
        anchors.verticalCenter: parent.verticalCenter
    }
}
