package org.apache.maven.wagon;

/* ====================================================================
 *   Copyright 2001-2004 The Apache Software Foundation.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * ====================================================================
 */

import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authorization.AuthorizationException;

import java.io.*;



public abstract class StreamWagon
    extends AbstractWagon
{
    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    public abstract InputStream getInputStream( String resource )
        throws TransferFailedException, ResourceDoesNotExistException;

    public abstract OutputStream getOutputStream( String resource )
        throws TransferFailedException;

    public abstract void openConnection()
        throws ConnectionException, AuthenticationException;

    public abstract void closeConnection()
        throws ConnectionException;

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    public void get( String resource, File destination )
        throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException
    {
        InputStream is = getInputStream( resource );

        if ( is == null )
        {
            throw new TransferFailedException( getRepository().getUrl() + " - Could not open input stream for resource: '" + resource+ "'" );
        }

        getTransfer( resource, destination, is );
    }

    // source doesn't exist exception
    public void put( File source, String resource )
        throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException
    {
        OutputStream os = getOutputStream( resource );

        if ( os == null )
        {
            throw new TransferFailedException( getRepository().getUrl() + " - Could not open output stream for resource: '" + resource+ "'" );
        }

        putTransfer( resource, source, os, true );
    }
}
