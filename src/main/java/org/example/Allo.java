package org.example;

import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

import java.io.IOException;

public class Allo extends AbstractCommand {
    @Override
    public void execute(FtpIoSession session, FtpServerContext context, FtpRequest request) throws IOException, FtpException {
        session.write(LocalizedFtpReply.translate(session, request, context,
                FtpReply.REPLY_200_COMMAND_OKAY, "ALLO", "bring it!"));
    }
}
