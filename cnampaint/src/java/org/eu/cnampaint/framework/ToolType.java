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
 * $Id: ToolType.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Iterator;


/**
 * Type �num�ratif d�finissant des outils graphiques.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public final class ToolType {
    //~ Initialisateurs et champs de classe ------------------------------------

    public static final ToolType RECTANGLE = new ToolType(Rectangle.class);
    public static final ToolType SQUARE  = new ToolType(Square.class);
    public static final ToolType ELLIPSE = new ToolType(Ellipse.class);
    public static final ToolType CIRCLE  = new ToolType(Circle.class);
    public static final ToolType LINE    = new ToolType(Line.class);
    public static final ToolType POINT   = new ToolType(Point.class);
    public static final ToolType NONE    = new ToolType(null);

    //~ Champs d'instance ------------------------------------------------------

    private final Class type;

    //~ Constructeurs ----------------------------------------------------------

    private ToolType(final Class type) {
        this.type = type;
    }

    //~ M�thodes ---------------------------------------------------------------

    public Class getType() {
        return type;
    }


    public Iterator iterator() {
        final ToolType[] types = {
                RECTANGLE, SQUARE, ELLIPSE, CIRCLE, POINT, LINE, NONE
            };

        return Arrays.asList(types).iterator();
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
