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
 * $Id: MessageHelper.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


/**
 * Classe utilitaire facilitant la création de messages graphiques à
 * destination de l'utilisateur.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class MessageHelper {
    //~ Champs d'instance ------------------------------------------------------

    private String commonTitle;

    //~ Méthodes ---------------------------------------------------------------

    public void setCommonTitle(String commonTitle) {
        this.commonTitle = commonTitle;
    }


    public String getCommonTitle() {
        return commonTitle;
    }


    public boolean showConfirmationMessage(Component parent, String msg,
        String title) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(parent,
            msg, getTitle(title), JOptionPane.YES_NO_OPTION);
    }


    public void showErrorMessage(Component parent, String msg, String title,
        Throwable e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        if (stackTraceElements == null) {
            stackTraceElements = new StackTraceElement[0];
        }

        final List stackTraceElementList = new ArrayList(stackTraceElements.length +
                1);
        stackTraceElementList.add(e.toString());

        for (int i = 0; i < stackTraceElements.length; ++i) {
            final StackTraceElement elem = stackTraceElements[i];
            stackTraceElementList.add("at " + elem);
        }

        final JList       list       = new JList(stackTraceElementList.toArray());
        final JScrollPane scrollPane = new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final Dimension minDim = new Dimension(400, 300);
        scrollPane.setMinimumSize(minDim);
        scrollPane.setPreferredSize(minDim);

        final JLabel label = new JLabel(msg);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JOptionPane.showMessageDialog(parent, panel, getTitle(title),
            JOptionPane.ERROR_MESSAGE);
    }


    public void showInformationMessage(Component parent, String msg,
        String title) {
        JOptionPane.showMessageDialog(parent, msg, getTitle(title),
            JOptionPane.INFORMATION_MESSAGE);
    }


    public Number showSpinnerQuestion(Component parent, String msg,
        String title, SpinnerNumberModel model) {
        final JSpinner spinner = new JSpinner(model);
        final JLabel   label = new JLabel(msg);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(spinner, BorderLayout.CENTER);

        final JOptionPane optionPane = new JOptionPane(panel,
                JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        optionPane.setWantsInput(false);
        final JDialog dialog = optionPane.createDialog(parent, getTitle(title));
        dialog.setVisible(true);

        return model.getNumber();
    }


    private String getTitle(String title) {
        if (title == null) {
            return (commonTitle == null) ? ""
                                         : commonTitle;
        }

        return (commonTitle == null) ? MnemonicUtils.removeMnemonicHint(title)
                                     : (MnemonicUtils.removeMnemonicHint(title) +
        " - " + commonTitle);
    }
}
