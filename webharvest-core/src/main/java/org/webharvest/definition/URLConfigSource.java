/*
 Copyright (c) 2006-2012 the original author or authors.

 Redistribution and use of this software in source and binary forms,
 with or without modification, are permitted provided that the following
 conditions are met:

 * Redistributions of source code must retain the above
   copyright notice, this list of conditions and the
   following disclaimer.

 * Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the
   following disclaimer in the documentation and/or other
   materials provided with the distribution.

 * The name of Web-Harvest may not be used to endorse or promote
   products derived from this software without specific prior
   written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */

package org.webharvest.definition;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.webharvest.utils.HasReader;

/**
 * Implementation of {@link ConfigSource} that uses a HTTP protocol as
 * source of XML configurations.
 *
 * @author Robert Bala
 * @since 2.1.0-SNAPSHOT
 * @version %I%, %G%
 * @see ConfigSource
 * @see URL
 */
public final class URLConfigSource implements ConfigSource {

    private final URLLocation location;

    /**
     * Default class constructor expecting {@link URL} as configuration source.
     *
     * @param url configuration source
     */
    public URLConfigSource(final URL url) {
        this.location = new URLLocation(url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reader getReader() throws IOException {
        return location.getReader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * Helper class representing wrapper for {@link URL} object that is adapted
     * to {@link Location} and {@link HasReader} interfaces.
     *
     * @author Robert Bala
     * @since 2.1.0-SNAPSHOT
     * @version %I%, %G%
     */
    private final class URLLocation implements Location, HasReader {

        private final URL url;

        /**
         * Default class constructor expecting reference to {@link URL}.
         *
         * @param file reference to {@link URL}.
         */
        public URLLocation(final URL url) {
            if (url == null) {
                throw new IllegalArgumentException("Configuration URL is "
                        + "required");
            }
            this.url = url;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Reader getReader() throws IOException {
            return new InputStreamReader(url.openStream());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return url.toString();
        }

    }

}
