/*
 * Created on 9/10/2003
 * YAWLEditor v1.0 
 *
 * @author Lindsay Bradford
 * 
 * 
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package au.edu.qut.yawl.editor.actions.specification;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import au.edu.qut.yawl.editor.actions.specification.YAWLOpenSpecificationAction;
import au.edu.qut.yawl.editor.specification.ArchivingThread;
import au.edu.qut.yawl.editor.swing.TooltipTogglingWidget;

public class ValidateSpecificationAction extends YAWLOpenSpecificationAction implements TooltipTogglingWidget {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  {
    putValue(Action.SHORT_DESCRIPTION, getDisabledTooltipText());
    putValue(Action.NAME, "Validate Specification");
    putValue(Action.LONG_DESCRIPTION, "Validate this specification.");
    putValue(Action.SMALL_ICON, getIconByName("Validate"));
    putValue(Action.MNEMONIC_KEY, new Integer(java.awt.event.KeyEvent.VK_V));
  }
  
  public void actionPerformed(ActionEvent event) {
    ArchivingThread.getInstance().validate();
  }
  
  public String getEnabledTooltipText() {
    return " Validate this specification ";
  }
  
  public String getDisabledTooltipText() {
    return " You must have an open specification" + 
           " to validate it ";
  }
}
