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
 * $Id: Model.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint;

import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.GraphicProperties;
import org.eu.cnampaint.framework.ToolType;

import java.io.File;


/**
 * Modèle de l'application. Contient l'ensemble des données partagées au sein
 * de l'application. Il est possible d'être informé de la modification d'une
 * propriété en enregistrant un <tt>PropertyChangeListener</tt> à l'aide de la
 * méthode héritée <tt>addPropertyChangeListener()</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class Model extends com.jgoodies.binding.beans.Model {
    //~ Champs d'instance ------------------------------------------------------

    private Draw              draw              = new Draw();
    private File              file;
    private GraphicProperties graphicProperties = new GraphicProperties();
    private ToolType          toolType          = ToolType.RECTANGLE;
    private boolean           saved;

    //~ Méthodes ---------------------------------------------------------------

    public void setDraw(Draw draw) {
        final Draw old = this.draw;
        this.draw = draw;
        firePropertyChange("draw", old, draw);
    }


    public Draw getDraw() {
        return draw;
    }


    public void setFile(File file) {
        final File old = this.file;
        this.file = file;
        firePropertyChange("file", old, file);
    }


    public File getFile() {
        return file;
    }


    public void setGraphicProperties(GraphicProperties graphicProperties) {
        final GraphicProperties old = this.graphicProperties;
        this.graphicProperties = graphicProperties;
        firePropertyChange("graphicProperties", old, graphicProperties);
    }


    public GraphicProperties getGraphicProperties() {
        return graphicProperties;
    }


    public void setSaved(boolean saved) {
        final boolean old = this.saved;
        this.saved = saved;
        firePropertyChange("saved", old, saved);
    }


    public boolean isSaved() {
        return saved;
    }


    public void setToolType(ToolType toolType) {
        final ToolType old = this.toolType;
        this.toolType = toolType;
        firePropertyChange("toolType", old, toolType);
    }


    public ToolType getToolType() {
        return toolType;
    }
}
