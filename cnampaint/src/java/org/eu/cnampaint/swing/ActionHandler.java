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
 * $Id: ActionHandler.java,v 1.2 2005/01/04 09:20:30 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eu.cnampaint.Application;
import org.eu.cnampaint.MessageSource;
import org.eu.cnampaint.Model;
import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.GraphicProperties;
import org.eu.cnampaint.framework.ToolType;
import org.eu.cnampaint.framework.event.DrawEvent;
import org.eu.cnampaint.framework.event.DrawListener;
import org.eu.cnampaint.framework.util.DrawInputStream;
import org.eu.cnampaint.framework.util.DrawOutputStream;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Random;

import javax.imageio.ImageIO;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;


/**
 * Gestionnaire d'évènements de l'application.
 *
 * @author alex
 * @version $Revision: 1.2 $, $Date: 2005/01/04 09:20:30 $
 */
public class ActionHandler {
    //~ Champs d'instance ------------------------------------------------------

    private final ActionLoader  actionLoader;
    private final Application   application;
    private final DrawListener  drawListener  = new MyDrawListener();
    private final Log           log           = LogFactory.getLog(getClass());
    private final MessageHelper messageHelper = new MessageHelper();
    private final Random        random        = new Random();

    //~ Constructeurs ----------------------------------------------------------

    public ActionHandler(final Application application,
        final ActionLoader actionLoader) {
        this.application      = application;
        this.actionLoader     = actionLoader;

        messageHelper.setCommonTitle(application.getMessageSource().getMessage("app.titre"));
    }

    //~ Méthodes ---------------------------------------------------------------

    public void aPropos() {
        final MessageSource messageSource = application.getMessageSource();
        messageHelper.showInformationMessage(null,
            messageSource.getMessage("app.titre") + ' ' +
            messageSource.getMessage("app.version") + " build " +
            messageSource.getMessage("build.number") + '\n' +
            messageSource.getMessage("app.copyright") + '\n' +
            messageSource.getMessage("app.license"),
            messageSource.getMessage("action.aPropos"));
    }


    public void couleurContour() {
        final GraphicProperties graphicProperties = (GraphicProperties) application.getModel()
                                                                                   .getGraphicProperties()
                                                                                   .clone();

        final MessageSource     messageSource = application.getMessageSource();
        final Color             color         = JColorChooser.showDialog(null,
                MnemonicUtils.removeMnemonicHint(messageSource.getMessage(
                        "action.couleurContour")) + " - " +
                messageSource.getMessage("app.titre"),
                graphicProperties.getBorderColor());
        if (color == null) {
            return;
        }

        graphicProperties.setBorderColor(color);
        application.getModel().setGraphicProperties(graphicProperties);
    }


    public void couleurFond() {
        final GraphicProperties graphicProperties = (GraphicProperties) application.getModel()
                                                                                   .getGraphicProperties()
                                                                                   .clone();

        final MessageSource     messageSource = application.getMessageSource();
        final Color             color         = JColorChooser.showDialog(null,
                MnemonicUtils.removeMnemonicHint(messageSource.getMessage(
                        "action.couleurFond")) + " - " +
                messageSource.getMessage("app.titre"),
                graphicProperties.getBackgroundColor());
        if (color == null) {
            return;
        }

        graphicProperties.setBackgroundColor(color);
        application.getModel().setGraphicProperties(graphicProperties);
    }


    public void enregistrerDessin() {
        final Model model = application.getModel();
        if (model.getFile() == null) {
            enregistrerSousDessin();

            return;
        }

        enregistrerDessin(model.getFile());
    }


    public void enregistrerSousDessin() {
        final JFileChooser fileChooser = createDefaultFileChooser();
        fileChooser.setSelectedFile(application.getModel().getFile());
        if (JFileChooser.APPROVE_OPTION != fileChooser.showSaveDialog(null)) {
            return;
        }

        if (fileChooser.getSelectedFile().exists()) {
            final MessageSource messageSource = application.getMessageSource();
            if (!messageHelper.showConfirmationMessage(null,
                        messageSource.getMessage("message.ecraserFichier"),
                        messageSource.getMessage("action.enregistrer"))) {
                return;
            }
        }

        enregistrerDessin(fileChooser.getSelectedFile());
    }


    public void epaisseurContour() {
        final MessageSource     messageSource     = application.getMessageSource();
        final GraphicProperties graphicProperties = (GraphicProperties) application.getModel()
                                                                                   .getGraphicProperties()
                                                                                   .clone();

        final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(graphicProperties.getBorderWidth(),
                0, 100, 1);
        final int                borderWidth = messageHelper.showSpinnerQuestion(null,
                messageSource.getMessage("message.attributs.epaisseurContour"),
                messageSource.getMessage("action.epaisseurContour"),
                spinnerModel).intValue();

        graphicProperties.setBorderWidth(borderWidth);
        application.getModel().setGraphicProperties(graphicProperties);
    }


    public void exporterDessin() {
        final JFileChooser fileChooser = createExportFileChooser();
        if (JFileChooser.APPROVE_OPTION != fileChooser.showSaveDialog(null)) {
            return;
        }

        File                file          = fileChooser.getSelectedFile();
        final MessageSource messageSource = application.getMessageSource();
        final String        extension     = "." +
            messageSource.getMessage("dessin.export.png.extension");
        if (!file.getAbsolutePath().toLowerCase().endsWith(extension)) {
            // ajout de l'extension au fichier si elle n'est pas présente
            file = new File(file.getParent(), file.getName() + extension);
        }

        if (file.exists()) {
            if (!messageHelper.showConfirmationMessage(null,
                        messageSource.getMessage("message.ecraserFichier"),
                        messageSource.getMessage("action.exporterDessin"))) {
                return;
            }
        }

        // rendu du dessin dans une image
        final Draw          draw  = application.getModel().getDraw();
        final BufferedImage image = new BufferedImage(draw.getWidth(),
                draw.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D    g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        draw.paint(g);
        g.dispose();

        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            log.error("Erreur durant l'exportation du dessin dans le fichier: " +
                file, e);
            messageHelper.showErrorMessage(null,
                messageSource.getMessage("erreur.exporterDessin"),
                messageSource.getMessage("action.exporterDessin"), e);
        }
    }


    public void fermerDessin() {
        nouveauDessin();
    }


    public void formePleine() {
        final GraphicProperties graphicProperties = (GraphicProperties) application.getModel()
                                                                                   .getGraphicProperties()
                                                                                   .clone();

        graphicProperties.setFilling(!graphicProperties.isFilling());

        application.getModel().setGraphicProperties(graphicProperties);
    }


    public void nouveauDessin() {
        verifierSiEnregistrerModifications();

        final MessageSource      messageSource = application.getMessageSource();
        final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(256, 1,
                4096, 1);
        final int                largeur = messageHelper.showSpinnerQuestion(null,
                messageSource.getMessage("message.nouveau.largeur"),
                MnemonicUtils.removeMnemonicHint(messageSource.getMessage(
                        "action.nouveauDessin")), spinnerModel).intValue();
        final int hauteur = messageHelper.showSpinnerQuestion(null,
                messageSource.getMessage("message.nouveau.hauteur"),
                MnemonicUtils.removeMnemonicHint(messageSource.getMessage(
                        "action.nouveauDessin")), spinnerModel).intValue();

        resetModel(new Draw(largeur, hauteur), null, false);
    }


    public void ouvrirDessin() {
        final JFileChooser fileChooser = createDefaultFileChooser();
        if (JFileChooser.APPROVE_OPTION != fileChooser.showOpenDialog(null)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Ouverture du dessin depuis le fichier: " +
                fileChooser.getSelectedFile());
        }

        final Model     model = application.getModel();

        DrawInputStream drawInputStream = null;
        try {
            drawInputStream = new DrawInputStream(new BufferedInputStream(
                        new FileInputStream(fileChooser.getSelectedFile())));
            resetModel(drawInputStream.read(), fileChooser.getSelectedFile(),
                true);
        } catch (Exception e) {
            log.error("Erreur durant l'ouverture du dessin depuis le fichier: " +
                fileChooser.getSelectedFile(), e);

            final MessageSource messageSource = application.getMessageSource();
            messageHelper.showErrorMessage(null,
                messageSource.getMessage("erreur.ouvrirDessin"),
                messageSource.getMessage("action.ouvrirDessin"), e);
            resetModel(new Draw(), null, false);
        } finally {
            if (drawInputStream != null) {
                try {
                    drawInputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }


    public void quitter() {
        verifierSiEnregistrerModifications();
        try {
            application.stop();
            application.dispose();
        } catch (Exception e) {
            application.fatal(e);
        }
    }


    public void selectionnerOutilCarre() {
        application.getModel().setToolType(ToolType.SQUARE);
    }


    public void selectionnerOutilCercle() {
        application.getModel().setToolType(ToolType.CIRCLE);
    }


    public void selectionnerOutilEllipse() {
        application.getModel().setToolType(ToolType.ELLIPSE);
    }


    public void selectionnerOutilLigne() {
        application.getModel().setToolType(ToolType.LINE);
    }


    public void selectionnerOutilRectangle() {
        application.getModel().setToolType(ToolType.RECTANGLE);
    }


    public void selectionnerOutilSelection() {
        application.getModel().setToolType(ToolType.NONE);
    }


    public void transparence() {
        final MessageSource     messageSource     = application.getMessageSource();
        final GraphicProperties graphicProperties = (GraphicProperties) application.getModel()
                                                                                   .getGraphicProperties()
                                                                                   .clone();

        final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(graphicProperties.getTransparency(),
                0, 1, 0.05f);
        final float              transparency = messageHelper.showSpinnerQuestion(null,
                messageSource.getMessage("message.attributs.transparence"),
                messageSource.getMessage("action.epaisseurContour"),
                spinnerModel).floatValue();

        graphicProperties.setTransparency(transparency);
        application.getModel().setGraphicProperties(graphicProperties);
    }


    private JFileChooser createDefaultFileChooser() {
        final MessageSource messageSource = application.getMessageSource();
        final Model         model = application.getModel();
        File                file  = model.getFile();
        if (file == null) {
            file = new File(messageSource.getMessage("dessin.nomParDefaut") +
                    "." + messageSource.getMessage("dessin.extension"));
        }

        final DefaultFileFilter fileFilter = new DefaultFileFilter(messageSource.getMessage(
                    "dessin.extension.desc"));
        fileFilter.addExtension(messageSource.getMessage("dessin.extension"));

        final JFileChooser fileChooser = new JFileChooser(file);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setMultiSelectionEnabled(false);

        return fileChooser;
    }


    private JFileChooser createExportFileChooser() {
        final MessageSource     messageSource = application.getMessageSource();
        final Model             model = application.getModel();

        final DefaultFileFilter fileFilter = new DefaultFileFilter(messageSource.getMessage(
                    "dessin.export.png.extension.desc"));
        fileFilter.addExtension(messageSource.getMessage(
                "dessin.export.png.extension"));

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setMultiSelectionEnabled(false);

        return fileChooser;
    }


    private void enregistrerDessin(File file) {
        final MessageSource messageSource = application.getMessageSource();
        final String        extension = "." +
            messageSource.getMessage("dessin.extension");
        if (!file.getAbsolutePath().toLowerCase().endsWith(extension)) {
            // ajout de l'extension au fichier si elle n'est pas présente
            file = new File(file.getParent(), file.getName() + extension);
        }

        if (log.isDebugEnabled()) {
            log.debug("Enregistrement du dessin dans le fichier: " +
                file.getPath());
        }

        DrawOutputStream drawOutputStream = null;
        try {
            drawOutputStream = new DrawOutputStream(new BufferedOutputStream(
                        new FileOutputStream(file)));
            drawOutputStream.write(application.getModel().getDraw());
            application.getModel().setSaved(true);
            application.getModel().setFile(file);
        } catch (Exception e) {
            log.error(
                "Erreur lors de l'enregistrement du dessin dans le fichier: " +
                file.getPath(), e);

            messageHelper.showErrorMessage(null,
                messageSource.getMessage("erreur.enregistrerDessin"),
                messageSource.getMessage("action.enregistrerDessin"), e);
        } finally {
            if (drawOutputStream != null) {
                try {
                    drawOutputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }


    private void resetModel(Draw draw, File file, boolean saved) {
        final Model model = application.getModel();
        draw.addDrawListener(drawListener);
        model.setDraw(draw);
        model.setFile(file);
        model.setSaved(saved);
    }


    private void verifierSiEnregistrerModifications() {
        if (!application.getModel().isSaved()) {
            final MessageSource messageSource = application.getMessageSource();
            if (messageHelper.showConfirmationMessage(null,
                        messageSource.getMessage(
                            "message.enregistrerModifications"),
                        messageSource.getMessage("action.fermerDessin"))) {
                enregistrerDessin();
            }
        }
    }

    //~ Classes ----------------------------------------------------------------

    private class MyDrawListener implements DrawListener {
        //~ Méthodes -----------------------------------------------------------

        public void graphicObjectAdded(DrawEvent evt) {
            application.getModel().setSaved(false);
        }
    }
}
