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
 * $Id: XStreamFactory.java,v 1.2 2005/01/04 09:19:53 romale Exp $
 */


package org.eu.cnampaint.framework.util;

import com.thoughtworks.xstream.XStream;

import org.eu.cnampaint.framework.Circle;
import org.eu.cnampaint.framework.Draw;
import org.eu.cnampaint.framework.Ellipse;
import org.eu.cnampaint.framework.Layer;
import org.eu.cnampaint.framework.Line;
import org.eu.cnampaint.framework.Point;
import org.eu.cnampaint.framework.Rectangle;
import org.eu.cnampaint.framework.Square;


/**
 * Classe utilitaire servant à la construction d'objets <tt>XStream</tt>.
 *
 * @author alex
 * @version $Revision: 1.2 $, $Date: 2005/01/04 09:19:53 $
 */
public final class XStreamFactory {
    //~ Constructeurs ----------------------------------------------------------

    private XStreamFactory() {
    }

    //~ Méthodes ---------------------------------------------------------------

    public static XStream createXStream() {
        final XStream xstream = new XStream();
        xstream.alias("draw", Draw.class);
        xstream.alias("layer", Layer.class);
        xstream.alias("circle", Circle.class);
        xstream.alias("square", Square.class);
        xstream.alias("rectangle", Rectangle.class);
        xstream.alias("ellipse", Ellipse.class);
        xstream.alias("line", Line.class);
        xstream.alias("point", Point.class);

        return xstream;
    }
}
