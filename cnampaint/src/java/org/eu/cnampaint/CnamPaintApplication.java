/*
 * Copyright (c) 2005, Alexandre ROMAN
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of the CnamPaint project nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: CnamPaintApplication.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint;

import com.jgoodies.plaf.LookUtils;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;
import com.jgoodies.plaf.plastic.theme.ExperienceBlue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.swing.ActionHandler;
import org.eu.cnampaint.swing.ActionLoader;
import org.eu.cnampaint.swing.ActionManager;
import org.eu.cnampaint.swing.MainFrame;
import org.eu.cnampaint.swing.MessageSourceActionLoader;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;

import org.picocontainer.defaults.DefaultPicoContainer;

import java.awt.Dimension;
import java.awt.Frame;

import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Implémentation concrète de l'interface <tt>Application</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class CnamPaintApplication extends AbstractApplication
  implements PicoContainerApplication {
    //~ Champs d'instance ------------------------------------------------------

    private final Log            log           = LogFactory.getLog(getClass());
    private final MessageSource  messageSource;
    private MutablePicoContainer picoContainer;

    //~ Constructeurs ----------------------------------------------------------

    public CnamPaintApplication() {
        final MessageSource resourceBundleMessageSource = new ResourceBundleMessageSource(ResourceBundle.getBundle(
                    "cnampaint"));

        final Properties    properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/build.properties"));
        } catch (Exception e) {
            log.warn("Erreur au chargement du fichier build.properties", e);
        }
        final MessageSource propertiesMessageSource = new PropertiesMessageSource(properties);

        messageSource = new CompositeMessageSource(Arrays.asList(
                    new MessageSource[] {
                        resourceBundleMessageSource, propertiesMessageSource
                    }));
    }

    //~ Méthodes ---------------------------------------------------------------

    public MessageSource getMessageSource() {
        return messageSource;
    }


    public PicoContainer getPicoContainer() {
        return picoContainer;
    }


    public void dispose() {
        super.dispose();

        if (picoContainer != null) {
            picoContainer.dispose();
            picoContainer = null;
        }
        System.exit(0);
    }


    public void init() {
        super.init();

        try {
            LookUtils.setLookAndTheme(new PlasticXPLookAndFeel(),
                new ExperienceBlue());
        } catch (Exception e) {
            log.error("Impossible d'installer le look and feel", e);
        }

        if (log.isInfoEnabled()) {
            log.info("Création du conteneur PicoContainer");
        }
        picoContainer = new DefaultPicoContainer();
        picoContainer.registerComponentInstance(this);
        picoContainer.registerComponentInstance(getMessageSource());

        picoContainer.registerComponentImplementation(MessageSourceActionLoader.class);
    }


    public void start() {
        super.start();

        picoContainer.start();

        final ActionLoader actionLoader = (ActionLoader) picoContainer.getComponentInstanceOfType(ActionLoader.class);

        final MainFrame    frame = new MainFrame(this,
                createActionManager(actionLoader,
                    new ActionHandler(this, actionLoader)));
        if (log.isInfoEnabled()) {
            log.info("Construction de l'interface");
        }
        frame.init();
        frame.setSize(640, 480);

        int width  = frame.getWidth();
        int height = frame.getHeight();

        // on ajuste la taille de la fenêtre en s'assurant qu'elle ne dépasse
        // pas la taille de l'écran
        final Dimension screenSize = frame.getToolkit().getScreenSize();
        boolean         maximize = false;
        if (frame.getWidth() > screenSize.width) {
            width        = screenSize.width;
            maximize     = true;
        }
        if (frame.getHeight() > screenSize.height) {
            height       = screenSize.height;
            maximize     = true;
        }
        frame.setSize(width, height);

        // on centre la fenêtre par rapport à l'écran, et on l'affiche
        frame.setLocationRelativeTo(null);

        if (maximize) {
            // agrandissement de la fenêtre si nécessaire
            // http://javaalmanac.com/egs/java.awt/frame_FrameIconify.html
            frame.setExtendedState(frame.getExtendedState() |
                Frame.MAXIMIZED_BOTH);
        }

        frame.setVisible(true);
    }


    public void stop() {
        super.stop();

        if (picoContainer != null) {
            picoContainer.stop();
        }
    }


    /**
     * Création d'un <tt>ActionManager</tt> contenant toutes les actions
     * utilisées dans l'application, initialisées par un <tt>ActionLoader</tt>
     * et rattachées à un gestionnaire d'action.
     *
     * @param actionLoader chargeur d'actions
     * @param actionHandler gestionnaire des actions
     *
     * @return un <tt>ActionManager</tt> initialisé avec toutes les actions
     *         disponibles
     */
    private ActionManager createActionManager(ActionLoader actionLoader,
        Object actionHandler) {
        final ActionManager actionManager = new ActionManager();

        actionManager.addAction("menu.fichier",
            actionLoader.load("menu.fichier"));
        actionManager.addAction("nouveauDessin",
            actionLoader.load("action.nouveauDessin", actionHandler,
                "nouveauDessin"));
        actionManager.addAction("ouvrirDessin",
            actionLoader.load("action.ouvrirDessin", actionHandler,
                "ouvrirDessin"));
        actionManager.addAction("fermerDessin",
            actionLoader.load("action.fermerDessin", actionHandler,
                "fermerDessin"));
        actionManager.addAction("enregistrerDessin",
            actionLoader.load("action.enregistrerDessin", actionHandler,
                "enregistrerDessin"));
        actionManager.addAction("enregistrerSousDessin",
            actionLoader.load("action.enregistrerSousDessin", actionHandler,
                "enregistrerSousDessin"));
        actionManager.addAction("exporterDessin",
            actionLoader.load("action.exporterDessin", actionHandler,
                "exporterDessin"));
        actionManager.addAction("quitter",
            actionLoader.load("action.quitter", actionHandler, "quitter"));

        actionManager.addAction("menu.attribut",
            actionLoader.load("menu.attribut"));
        actionManager.addAction("epaisseurContour",
            actionLoader.load("action.epaisseurContour", actionHandler,
                "epaisseurContour"));
        actionManager.addAction("couleurContour",
            actionLoader.load("action.couleurContour", actionHandler,
                "couleurContour"));
        actionManager.addAction("couleurFond",
            actionLoader.load("action.couleurFond", actionHandler, "couleurFond"));
        actionManager.addAction("transparence",
            actionLoader.load("action.transparence", actionHandler,
                "transparence"));
        actionManager.addAction("formePleine",
            actionLoader.load("action.formePleine", actionHandler, "formePleine"));

        actionManager.addAction("menu.aide", actionLoader.load("menu.aide"));
        actionManager.addAction("aPropos",
            actionLoader.load("action.aPropos", actionHandler, "aPropos"));

        actionManager.addAction("menu.forme", actionLoader.load("menu.forme"));
        actionManager.addAction("outilRectangle",
            actionLoader.load("action.outilRectangle", actionHandler,
                "selectionnerOutilRectangle"));
        actionManager.addAction("outilCarre",
            actionLoader.load("action.outilCarre", actionHandler,
                "selectionnerOutilCarre"));
        actionManager.addAction("outilCercle",
            actionLoader.load("action.outilCercle", actionHandler,
                "selectionnerOutilCercle"));
        actionManager.addAction("outilEllipse",
            actionLoader.load("action.outilEllipse", actionHandler,
                "selectionnerOutilEllipse"));
        actionManager.addAction("outilLigne",
            actionLoader.load("action.outilLigne", actionHandler,
                "selectionnerOutilLigne"));
        actionManager.addAction("outilSelection",
            actionLoader.load("action.outilSelection", actionHandler,
                "selectionnerOutilSelection"));

        return actionManager;
    }
}
