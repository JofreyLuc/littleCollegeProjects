TEMPLATE = app

QT += qml quick widgets

SOURCES += main.cpp \
    WindowHandler.cpp \
	BDD.cpp \
	Produit.cpp \
	StateConnecte.cpp \
	StateDeconnecte.cpp \
	StateEnchere.cpp \
	StateNormal.cpp \
	StateProduit.cpp \
	StateUtilisateur.cpp \
	Utilisateur.cpp

RESOURCES += qml.qrc

# Additional import path used to resolve QML modules in Qt Creator's code model
QML_IMPORT_PATH =

# Default rules for deployment.
include(deployment.pri)

HEADERS += \
    WindowHandler.hpp \
	BDD.hpp \
	Produit.hpp \
	StateConnecte.hpp \
	StateDeconnecte.hpp \
	StateEnchere.hpp \
	StateNormal.hpp \
	StateProduit.hpp \
	StateUtilisateur.hpp \
	Utilisateur.hpp \
