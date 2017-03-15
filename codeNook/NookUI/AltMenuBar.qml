import QtQuick 2.3
import QtQuick.Controls 1.2

MenuBar {
    Menu {
        title: qsTr("&Fichier")

        MenuItem {
            text: qsTr("Quitter")
            onTriggered: Qt.quit()
            shortcut: "Ctrl+Q"
        }
    }
}
