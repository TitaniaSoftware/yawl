/*
 * Created on 20/12/2003
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

package org.yawlfoundation.yawl.editor.ui.elements.view;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;
import org.yawlfoundation.yawl.editor.ui.elements.model.Decorator;
import org.yawlfoundation.yawl.editor.ui.elements.model.JoinDecorator;
import org.yawlfoundation.yawl.editor.ui.elements.model.SplitDecorator;
import org.yawlfoundation.yawl.editor.ui.util.UserSettings;

import java.awt.*;

public class DecoratorView extends VertexView {
  
  private DecoratorRenderer decoratorRenderer;

  public DecoratorView(Decorator decorator) {
    super(decorator);
    
    if (decorator instanceof JoinDecorator) {
      decoratorRenderer = new JoinDecoratorRenderer((JoinDecorator) decorator);
    }
    if (decorator instanceof SplitDecorator) {
      decoratorRenderer = new SplitDecoratorRenderer((SplitDecorator) decorator);
    }      
  }

  public CellViewRenderer getRenderer() {
    return decoratorRenderer;
  }
}

class JoinDecoratorRenderer extends DecoratorRenderer {

  public JoinDecoratorRenderer(JoinDecorator joinDecorator) {
    super(joinDecorator);
  }
  
  protected Color getFillColor() {
      return UserSettings.getJoinFillColour();
  }
}

class SplitDecoratorRenderer extends DecoratorRenderer {

  public SplitDecoratorRenderer(SplitDecorator splitDecorator) {
    super(splitDecorator);
  }
  
  protected Color getFillColor() {
    return UserSettings.getSplitFillColour();
  }
}

abstract class DecoratorRenderer extends YAWLVertexRenderer {

  private static Color WHITE_FILL = Color.WHITE;
  private Decorator decorator;
  
  public DecoratorRenderer(Decorator decorator) {
    this.decorator = decorator;
  }

  public boolean isDecoratingConfigurableTask() {
      return (decorator != null) && decorator.getTask().isConfigurable();
  }
  
  protected void fillVertex(Graphics graphics, Dimension size) {
    graphics.fillRect(0, 0, size.width - 1, size.height - 1);
  }
  
  abstract protected Color getFillColor();

  
  protected void drawVertex(Graphics graphics, Dimension size) {
    
    switch(decorator.getCardinalPosition()) {
      case Decorator.TOP: {
        switch(decorator.getType()) {
          case Decorator.AND_TYPE: {
            drawDownwardTriangle(
                graphics,
                size, 
                WHITE_FILL, 
                decorator.getTask().getBackgroundColor() // getFillColor()
            );
            break;
          }
          case Decorator.OR_TYPE: {
            drawDiamond(
                graphics, 
                size, 
                WHITE_FILL, 
                decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.XOR_TYPE: {
            drawUpwardTriangle(
                graphics,
                size, 
                    decorator.getTask().getBackgroundColor(), //getFillColor(),
                WHITE_FILL
            );
            break;
          }
        }
        break;
      }
      case Decorator.BOTTOM: {
        switch(decorator.getType()) {
          case Decorator.AND_TYPE: {
            drawUpwardTriangle(
                graphics,
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.OR_TYPE: {
            drawDiamond(
                graphics, 
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.XOR_TYPE: {
            drawDownwardTriangle(
                graphics,
                size, 
                    decorator.getTask().getBackgroundColor(), //getFillColor(),
                WHITE_FILL
            );
            break;
          }
        }
        break;
      }
      case Decorator.LEFT: {
        switch(decorator.getType()) {
          case Decorator.AND_TYPE: {
            drawRightwardTriangle(
                graphics, 
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.OR_TYPE: {
            drawDiamond(
                graphics, 
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.XOR_TYPE: {
            drawLeftwardTriangle(
                graphics, 
                size, 
                    decorator.getTask().getBackgroundColor(), //getFillColor(),
                WHITE_FILL
            );
            break;
          }
        }
        break;
      }
      case Decorator.RIGHT: {
        switch(decorator.getType()) {
          case Decorator.AND_TYPE: {
            drawLeftwardTriangle(
                graphics, 
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.OR_TYPE: {
            drawDiamond(
                graphics, 
                size, 
                WHITE_FILL, 
                    decorator.getTask().getBackgroundColor() //getFillColor()
            );
            break;
          }
          case Decorator.XOR_TYPE: {
            drawRightwardTriangle(
                graphics, 
                size, 
                    decorator.getTask().getBackgroundColor(), //getFillColor(),
                WHITE_FILL
            );
            break;
          }
        }
        break;
      }
    }

  }
  
  /**
   * Draws a decorator. The basic process is to fill the decorator space with
   * the color specified by backingColor, then to fill the area taken by the polygon
   * shape of the decorator with it's polygonColor, then to draw a border around the 
   * polygon, and then finally, draw a border around the entire decorator.
   * @param xCoords
   * @param yCoords
   * @param graphics
   * @param size
   * @param backingColor
   * @param polygonColor
   */
  
  private void drawSymbol(int[] xCoords, int[] yCoords, Graphics graphics, Dimension size, 
                          Color backingColor, Color polygonColor) {

    Color originalColor = graphics.getColor();

    /* Fill the entire decorator with the specified backing color */
    
    graphics.setColor(backingColor);
    graphics.fillRect(0, 0, size.width, size.height);
    
    /* fill the symbol polygon with it's needed color */
    
    graphics.setColor(polygonColor);
    graphics.fillPolygon(xCoords, yCoords, xCoords.length);
    
    /* draw the symbol polygon's border */
    
    graphics.setColor(originalColor);
    graphics.drawPolygon(xCoords, yCoords, xCoords.length);

    /* draw the border of the decorator */

    if (isDecoratingConfigurableTask()) {
        Stroke oldStroke = ((Graphics2D) graphics).getStroke();
        ((Graphics2D) graphics).setStroke(new BasicStroke(CONFIGURED_TASK_STOKE_WIDTH));
        int position = decorator.getCardinalPosition();
        if (position != Decorator.BOTTOM)
            graphics.drawLine(0, 0, size.width - 1, 0);                              // top
        if (position != Decorator.TOP)
            graphics.drawLine(0, size.height - 1, size.width - 1, size.height - 1); // bottom
        if (position != Decorator.LEFT)
            graphics.drawLine(size.width - 1, 0, size.width - 1, size.height - 1);  // right
        if (position != Decorator.RIGHT)
            graphics.drawLine(0, 0, 0, size.height - 1);                            // left
        ((Graphics2D) graphics).setStroke(oldStroke);
    }
    else graphics.drawRect(0, 0, size.width - 1, size.height - 1);

  }
  
  private void drawDownwardTriangle(Graphics graphics,Dimension size, Color backingColor, Color polygonColor) {
    int[] xPoints = new int[] {
      0, Math.round(size.width/2), size.width - 0  
    };
    int[] yPoints = new int[] {
        0, size.height - 1, 0  
    };
   
    drawSymbol(xPoints, yPoints, graphics, size, backingColor, polygonColor);
  }

  private void drawLeftwardTriangle(Graphics graphics,Dimension size, Color backingColor, Color polygonColor) {
    int[] xPoints = new int[] {
        size.width, 1, size.width
    };
    int[] yPoints = new int[] {
          0, Math.round(size.height/2), size.height
    };
    
    drawSymbol(xPoints, yPoints, graphics, size, backingColor, polygonColor);
  }  

  private void drawRightwardTriangle(Graphics graphics,Dimension size, Color backingColor, Color polygonColor) {
    int[] xPoints = new int[] {
      0, size.width - 1, 0
    };
    int[] yPoints = new int[] {
      0, Math.round(size.height/2), size.height
    };

    drawSymbol(xPoints, yPoints, graphics, size, backingColor, polygonColor);
  }  
  
  private void drawUpwardTriangle(Graphics graphics,Dimension size, Color backingColor, Color polygonColor) {
    int[] xPoints = new int[] {
        0, Math.round(size.width/2), size.width 
    };
    int[] yPoints = new int[] {
        size.height, 1, size.height
    };

    drawSymbol(xPoints, yPoints, graphics, size, backingColor, polygonColor);
  }

  private void drawDiamond(Graphics graphics,Dimension size, Color backingColor, Color polygonColor) {
    int[] xPoints = new int[] {
      1, Math.round(size.width/2), size.width - 1, Math.round(size.width/2)
    };
    int[] yPoints = new int[] {
      Math.round(size.height/2), 1, Math.round(size.height/2), size.height - 1
    };

    drawSymbol(xPoints, yPoints, graphics, size, backingColor, polygonColor);
  }
}